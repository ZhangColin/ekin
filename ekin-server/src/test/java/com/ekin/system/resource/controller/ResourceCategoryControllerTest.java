//package com.ekin.system.resource.controller;
//
//import com.ekin.EkinApplication;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("test")
//@SpringBootTest(classes = EkinApplication.class)
//public class ResourceCategoryControllerTest {
//    @Autowired
//    private ResourceCategoryController controller;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    }
//
//    @Test
//    public void getAllResourceCategories() throws Exception {
//        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .get("/system/resources/categories")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON_UTF8);
//
//        final MvcResult mvcResult = mockMvc.perform(request).andReturn();
//        System.out.println(mvcResult.getResponse().getContentAsString());
//    }
//
//    @Test
//    public void addResourceCategory() {
//    }
//
//    @Test
//    public void editResourceCategory() {
//    }
//
//    @Test
//    public void removeResourceCategory() {
//    }
//}