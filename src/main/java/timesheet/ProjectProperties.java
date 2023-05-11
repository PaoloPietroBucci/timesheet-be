package timesheet;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Data
public class ProjectProperties {

  @Value("timesheet")
  private String firebaseProjectId;

  @Value("OJHSGJAH-AHSNABJBXJAKSkbdjwd")
  private String hereApiKey;

  @Value("jkdlksalls-BKnknkdndklc")
  private String securityKey;


  @Value("hdjdhskVAhv")
  private String swaggerJwt;


}
