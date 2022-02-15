package guru.springframework.msscbrewrey.services.inventory;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Configuration
public class WiremockInitialiser implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    public static WireMockRuntimeInfo wireMockRuntimeInfo;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        // cannot set this port dynamically due to the initializers lifecycle. this
        // runs before any springboot property injection can occur
        WireMockServer wireMockServer = new WireMockServer(9090);
        wireMockRuntimeInfo =  new WireMockRuntimeInfo(wireMockServer);

        wireMockServer.start();
        // register with the spring bean context
        configurableApplicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);
        configurableApplicationContext.getBeanFactory().registerSingleton("wireMockRuntimeInfo", wireMockRuntimeInfo);

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

    }

    public static WireMockRuntimeInfo getWireMockRuntimeInfo() {
        return wireMockRuntimeInfo;
  }
}