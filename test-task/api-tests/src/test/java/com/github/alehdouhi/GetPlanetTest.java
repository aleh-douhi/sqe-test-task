package com.github.alehdouhi;

import com.github.alehdouhi.builder.PlanetContractMother;
import com.github.alehdouhi.model.Planet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import jakarta.xml.ws.WebServiceException;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPlanetTest {
    @Test
    public void get_secondPlanet_returnsAlderaan() {
        // arrange
        final Planet expectedPlanet = PlanetContractMother.getAlderaan().build();
        // act
        Planet result = SwapiRestClient.instance().getPlanet("2");
        // assert
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expectedPlanet);
    }

    @DataProvider(name = "id-data-provider")
    public Object[][] idDataProviderMethod() {
        return new Object[][] { { "0" }, { "100" }, { "test" } };
    }

    @Test(dataProvider = "id-data-provider")
    public void get_notExistingPlanet_returnsNotFound(String id) {
        // arrange
        final String expectedStatusCode = "404";
        // act
        WebServiceException ex = Assert.expectThrows(WebServiceException.class, () -> SwapiRestClient.instance().getPlanet(id));
        // assert
        Assert.assertEquals(ex.getMessage(), expectedStatusCode);
    }
}