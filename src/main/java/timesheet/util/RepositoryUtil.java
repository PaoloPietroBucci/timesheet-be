package timesheet.util;

import timesheet.libs.models.Pagination;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import java.util.List;

public class RepositoryUtil {

    public static String queryDistance = "CUSTOM_DISTANCE_SPHERE(POINT(:latitude, :longitude), POINT(rl.latitude , rl.longitude)) AS distance ";

    /**
     * @param typedQuery TypedQuery<T>
     * @param pagination Pagination
     * @param <T>        T
     * @return List<T>
     */
    public static <T> List<T> addPagination(Query typedQuery, Pagination pagination) {
        if (pagination != null && pagination.getPage() != null) {
            typedQuery
                    .setFirstResult((pagination.getPage() - 1) * pagination.getElementsPerPage())
                    .setMaxResults(pagination.getElementsPerPage());
        }
        return typedQuery.getResultList();
    }

    /**
     * @param builder CriteriaBuilder
     * @param orders  List<Order>
     * @param field   Path<T> field
     * @param value   String
     * @param <T>     T
     */
    public static <T> void addOrder(CriteriaBuilder builder, List<Order> orders, Path<T> field, String value) {
        if (GenericUtil.isNotNullOrEmpty(value)) {
            if (value.equals("asc")) {
                orders.add(builder.asc(field));
            } else {
                orders.add(builder.desc(field));
            }
        }
    }

    /**
     * @return String
     */
    public static String querySupplier(boolean distance) {
        return "SELECT DISTINCT s.*, " + (distance ? " CUSTOM_DISTANCE_SPHERE(POINT(:latitude, :longitude), POINT(sl.latitude , sl.longitude))" : " null") + " AS distance \n" +
                "  FROM supplier s \n" +
                "  JOIN supplier_location sl ON sl.id_supplier = s.id AND sl.deleted = FALSE \n" +
                " WHERE s.deleted = FALSE \n" +
                " ORDER BY distance ASC, s.name";
    }

    /**
     * @param lite     boolean
     * @param distance boolean
     * @param isOpen   boolean
     * @param discount boolean
     * @return String
     */
    public static String fields(boolean lite, boolean distance, boolean isOpen, boolean discount) {
        if (lite) {
            return " r.id ";
        }
        return " r.*, w.id_whitelabel " +
                ", rl.latitude " +
                ", rl.longitude " +
                ", " + (distance ? queryDistance : "NULL AS distance ") +
                ", " + (isOpen ? "TRUE" : "FALSE") + " AS is_open " +
                // TODO ", " + (discount ? "a.discount" : "NULL") + " AS max_discount ";
                ", NULL AS max_discount ";
    }

    /**
     * @param lite         boolean
     * @param distance     boolean
     * @param categoryIn   boolean
     * @param discount     boolean
     * @param deliver      boolean
     * @param idRestaurant Long
     * @return String
     */
    public static String queryMatch(boolean lite, boolean distance, boolean categoryIn, boolean discount, boolean deliver, Long idRestaurant) {
        return "SELECT DISTINCT " + fields(lite, distance, true, true) + " \n" +
                "  FROM restaurant r \n" +
                joinDiscount() +
                "  JOIN restaurant_location rl ON rl.id = r.id AND rl.deleted = FALSE \n" +
                (deliver ? deliver() : "") +
                (categoryIn ? categoryIn() : "") +
                "  JOIN restaurant_timetable rt ON \n" +
                "    rt.id_restaurant = r.id \n" +
                "    AND rt.weekday = :weekday \n" +
                "    AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(rt.time_from, '%H:%i') \n" +
                "    AND STR_TO_DATE(:time, '%H:%i') <= STR_TO_DATE(rt.time_to, '%H:%i') \n" +
                "    AND rt.deleted = FALSE \n" +
                "  JOIN restaurant_option ro ON \n" +
                "    ro.id_restaurant = r.id \n" +
                "    AND ro.option = :option \n" +
                "    AND (ro.offline_till is null OR :requestDate >= ro.offline_till) \n" +
                "    AND ro.deleted = FALSE \n" +
                "    AND ro.enable = TRUE \n" +
                "  JOIN restaurant_option_timetable rot ON \n" +
                "    rot.id_restaurant_option = ro.id \n" +
                "    AND rot.weekday = :weekday \n" +
                "    AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(rot.time_from, '%H:%i') \n" +
                "    AND STR_TO_DATE(:time, '%H:%i') < STR_TO_DATE(rot.time_to, '%H:%i') \n" +
                "    AND rot.deleted = FALSE \n" +
                "    AND rot.enable = TRUE \n" +
                "  JOIN restaurant_whitelabel w ON \n" +
                "    w.id_restaurant = r.id AND w.deleted = FALSE \n" +
                " WHERE r.deleted = FALSE \n" +
                "   AND r.active = TRUE \n" +
                "   AND r.name like :name \n" +
                (GenericUtil.isNotNullOrEmpty(idRestaurant) ? "   AND r.id = " + idRestaurant + " \n" : "") +
                "   AND w.id_whitelabel = :whitelabel \n" +
                "   AND (r.offline_till is null OR :requestDate >= r.offline_till) \n" +
                "   AND ( \n" +
                "     SELECT count(*) \n" +
                "       FROM restaurant_day_off rdo \n" +
                "      WHERE rdo.id_restaurant = r.id \n" +
                "        AND :requestDate BETWEEN rdo.date_from AND rdo.date_to \n" +
                "        AND rdo.deleted = FALSE \n" +
                "   ) = 0 " +
                (distance ? distance(deliver) : "") +
                (discount ? discount() : "");
    }

    /**
     * @return String
     */
    private static String deliver() {
        return " JOIN v_restaurant_deliver rd ON rd.id_restaurant = r.id \n";
    }

    /**
     * @return String
     */
    private static String categoryIn() {
        return " JOIN restaurant_restaurant_category rrc ON rrc.id_restaurant = r.id AND rrc.id_restaurant_category in (:categories) and rrc.deleted = FALSE \n";
    }

    /**
     * @return String
     */
    private static String distance(boolean deliver) {
        return "\n   AND CUSTOM_DISTANCE_SPHERE(POINT(:latitude, :longitude), POINT(rl.latitude , rl.longitude)) < " + (deliver ? "rd.km_to " : ":maxDistance ");
    }

    /**
     * @return String
     */
    public static String joinDiscount() {
        return "  LEFT JOIN (\n" +
                "    select max(d.discount) as discount, d.id_restaurant\n" +
                "      from (\n" +
                "        select max(rdt.discount) as discount, rdt.id_restaurant\n" +
                "         from restaurant_discount_timetable rdt\n" +
                "        where rdt.weekday = :weekday\n" +
                "          AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(rdt.time_from, '%H:%i')\n" +
                "          AND STR_TO_DATE(:time, '%H:%i') < STR_TO_DATE(rdt.time_to, '%H:%i')\n" +
                "          AND rdt.deleted = FALSE\n" +
                "       union\n" +
                "       select max(crt.discount) as discount, cr.id_restaurant\n" +
                "         from category_restaurant cr\n" +
                "         join category_restaurant_timetable crt ON crt.id_category_restaurant = cr.id AND crt.weekday = :weekday AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(crt.time_from, '%H:%i') AND STR_TO_DATE(:time, '%H:%i') < STR_TO_DATE(crt.time_to, '%H:%i') AND crt.enable = TRUE AND crt.discount IS NOT NULL AND crt.discount > 0 AND crt.deleted = FALSE AND crt.option = :option\n" +
                "       union\n" +
                "       select max(prt.discount) as discount, pr.id_restaurant\n" +
                "         from product_restaurant pr\n" +
                "         join product_restaurant_timetable prt ON prt.id_product_restaurant = pr.id AND prt.weekday = :weekday AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(prt.time_from, '%H:%i') AND STR_TO_DATE(:time, '%H:%i') < STR_TO_DATE(prt.time_to, '%H:%i') AND prt.enable = TRUE AND prt.discount IS NOT NULL AND prt.discount > 0 AND prt.deleted = FALSE AND prt.option = :option\n" +
                "      ) as d\n" +
                "    group by d.id_restaurant\n" +
                "  ) as a ON a.id_restaurant = r.id \n";
    }

    /**
     * @return String
     */
    public static String discount() {
        return "\n   AND ( \n" +
                discountRestaurant() +
                "     OR " +
                discountCategories() +
                "     OR " +
                discountProducts() +
                "   )";
    }

    /**
     * @return String
     */
    private static String discountRestaurant() {
        return "     exists ( \n" +
                "       select 'x' \n" +
                "         from restaurant_discount_timetable rdt \n" +
                "        where rdt.id_restaurant = r.id AND rdt.weekday = :weekday AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(rdt.time_from, '%H:%i') AND STR_TO_DATE(:time, '%H:%i') < STR_TO_DATE(rdt.time_to, '%H:%i') AND rdt.deleted = FALSE \n" +
                "     ) \n";
    }

    /**
     * @return String
     */
    private static String discountCategories() {
        return " exists ( \n" +
                "       select 'x' \n" +
                "         from category_restaurant cr \n" +
                "         join category_restaurant_timetable crt ON crt.id_category_restaurant = cr.id AND crt.weekday = :weekday AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(crt.time_from, '%H:%i') AND STR_TO_DATE(:time, '%H:%i') < STR_TO_DATE(crt.time_to, '%H:%i') AND crt.enable = TRUE AND crt.discount IS NOT NULL AND crt.discount > 0 AND crt.deleted = FALSE AND crt.option = :option \n" +
                "        where cr.id_restaurant = r.id \n" +
                "     ) \n";
    }

    /**
     * @return String
     */
    private static String discountProducts() {
        return " exists ( \n" +
                "       select 'x' \n" +
                "         from product_restaurant pr \n" +
                "         join product_restaurant_timetable prt ON prt.id_product_restaurant = pr.id AND prt.weekday = :weekday AND STR_TO_DATE(:time, '%H:%i') >= STR_TO_DATE(prt.time_from, '%H:%i') AND STR_TO_DATE(:time, '%H:%i') < STR_TO_DATE(prt.time_to, '%H:%i') AND prt.enable = TRUE AND prt.discount IS NOT NULL AND prt.discount > 0 AND prt.deleted = FALSE AND prt.option = :option \n" +
                "        where pr.id_restaurant = r.id \n" +
                "     ) \n";
    }

    /**
     * @param distance boolean
     * @return String
     */
    public static String orderBy(boolean distance) {
        if (distance) {
            return " is_open DESC, distance ASC, name ASC ";
        }
        return " is_open DESC, name ASC ";
    }

}