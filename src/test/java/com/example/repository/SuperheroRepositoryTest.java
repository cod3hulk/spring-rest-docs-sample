package com.example.repository;

import com.example.DemoApplication;
import com.example.domain.Superhero;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;


/**
 * Test class for superhero repository
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
    classes = DemoApplication.class)
@WebAppConfiguration
public class SuperheroRepositoryTest {

    @Rule
    public final RestDocumentation restDoc = new RestDocumentation("build/generated-snippets");

    @Autowired
    private EntityLinks entityLinks;

    @Autowired
    private WebApplicationContext context;

    private URI superheroesURI;


    @Before
    public void setUp() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
            .apply(
                documentationConfiguration(this.restDoc)
            )
            .build();
        RestAssuredMockMvc.mockMvc(mockMvc);
        superheroesURI = entityLinks.linkFor(Superhero.class).toUri();
    }

    @Test
    public void test_find_all() {
         // generate docs based on result
        RestDocumentationResultHandler docResultHandler = document("superhero-find-all",
            responseFields(
                fieldWithPath("_embedded").description("Embedded list of <<resources-superhero-find, superheroes>>"),
                fieldWithPath("page").description("Information about pagination"),
                fieldWithPath("_links").description("Links to requested resource")
            )
        );

        //@formatter:off
        given()
            .resultHandlers(
                docResultHandler
            )
        .when()
            .get(superheroesURI)
        .then()
            .statusCode(200)
            .body("_embedded.superheroes[0].name", is("Wolverine"))
            .body("_embedded.superheroes[0].universe", is("Marvel"))
            .body("_embedded.superheroes[1].name", is("The Flash"))
            .body("_embedded.superheroes[1].universe", is("DC"));
        //@formatter:on
    }

    @Test
    public void test_find() {
        // generate docs based on result
        RestDocumentationResultHandler docResultHandler = document("superhero-find",
            responseFields(
                fieldWithPath("name").description("Name of the superhero"),
                fieldWithPath("universe").description("Universe the superhero lives in"),
                fieldWithPath("_links").description("Links to requested resource")
            )
        );

        LinkBuilder singleResourceLink = entityLinks.linkForSingleResource(Superhero.class, 1);

        //@formatter:off
        given()
            .resultHandlers(
                docResultHandler
            )
        .when()
            .get(singleResourceLink.toUri())
        .then()
            .statusCode(200)
            .body("name", is("Wolverine"))
            .body("universe", is("Marvel"));
        //@formatter:on
    }


    @Test
    public void test_create() {
        // generate docs based on result
        RestDocumentationResultHandler docResultHandler = document("superhero-create",
            requestFields(
                fieldWithPath("name").description("Name of the superhero"),
                fieldWithPath("universe").description("Universe the superhero lives in")
            )
        );

        Superhero superhero = new Superhero("Silver Surfer", "Marvel");

        //@formatter:off
        given()
            .resultHandlers(
                docResultHandler
            )
            .contentType(ContentType.JSON)
            .body(superhero)
        .when()
            .post(superheroesURI)
        .then()
            .statusCode(201);
        //@formatter:on
    }

}
