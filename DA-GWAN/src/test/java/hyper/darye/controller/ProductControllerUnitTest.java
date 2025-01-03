package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.dto.controller.request.RequestPostProductDto;
import hyper.darye.security.SecurityConfig;
import hyper.darye.service.ProductService;
import hyper.darye.testConfig.mockUser.WithMockCustomUser;
import hyper.darye.validation.FieldCompare.FieldComparisonValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ProductController.class)
@Import({SecurityConfig.class, FieldComparisonValidator.class})
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("상품, 상품이미지 등록 - 관리자")
    public void testInsertProduct() throws Exception {
        // 테스트용 상품 데이터 생성
        RequestPostProductDto requestDto = new RequestPostProductDto();
        requestDto.setName("테스트 상품");
        requestDto.setCategoryId(1L);

        // JSON 데이터를 MockMultipartFile로 생성
        MockMultipartFile requestDtoFile = new MockMultipartFile(
                "insertPostProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(requestDto).getBytes()
        );

        // 테스트용 이미지 파일 생성
        byte[] thumbnailImageBytes = { 0x01, 0x02, 0x03 }; // 썸네일 이미지 데이터
        MockMultipartFile thumbnailImageFile = new MockMultipartFile(
                "thumbnailImage", // Controller에서 @RequestPart의 파라미터 이름과 일치해야 함
                "thumbnail.png",
                MediaType.IMAGE_PNG_VALUE,
                thumbnailImageBytes
        );

        byte[] descriptionImageBytes = { 0x04, 0x05, 0x06 }; // 상세 설명 이미지 데이터
        MockMultipartFile descriptionImageFile = new MockMultipartFile(
                "descriptionImage", // Controller에서 @RequestPart의 파라미터 이름과 일치해야 함
                "description.png",
                MediaType.IMAGE_PNG_VALUE,
                descriptionImageBytes
        );

        // ProductService의 insertProduct 메서드가 호출될 때 예외를 발생시키지 않도록 설정
        when(productService.insertProduct(any())).thenReturn(1);

        // 컨트롤러 호출
        mockMvc.perform(multipart("/api/products")
                        .file(requestDtoFile) // JSON 데이터
                        .file(thumbnailImageFile) // 썸네일 이미지 파일
                        .file(descriptionImageFile) // 상세 설명 이미지 파일
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated()) // 생성 성공
                .andDo(print());
    }

    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("상품 등록 - 이미지 없이 관리자")
    public void testInsertProductWithoutImages() throws Exception {
        // 테스트용 상품 데이터 생성
        RequestPostProductDto requestDto = new RequestPostProductDto();
        requestDto.setName("테스트 상품");
        requestDto.setCategoryId(1L);

        // JSON 데이터를 MockMultipartFile로 생성
        MockMultipartFile requestDtoFile = new MockMultipartFile(
                "insertPostProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(requestDto).getBytes()
        );

        // ProductService의 insertProduct 메서드가 호출될 때 예외를 발생시키지 않도록 설정
        when(productService.insertProduct(any())).thenReturn(1);

        // 컨트롤러 호출
        mockMvc.perform(multipart("/api/products")
                        .file(requestDtoFile) // JSON 데이터만 추가
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated()) // 생성 성공
                .andDo(print());
    }


    @Test
    @WithMockCustomUser(role = "USER")
    @DisplayName("상품 등록 - 일반 유저 실패")
    void insertPostProductUnitTest2() throws Exception {
        // given
        given(productService.insertProduct(any(ProductWithBLOBs.class))).willReturn(1);

        String expirationDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String saleDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        String jsonContent = """
    {
        "id": 1,
        "categoryId": 1,
        "productStatusCodeId": 1,
        "name": "title",
        "price": 100,
        "expirationDate": "%s",
        "saleDate": "%s",
        "quantity": 2
    }
    """.formatted(expirationDate, saleDate);

        MockMultipartFile requestPart = new MockMultipartFile(
                "insertPostProductRequest",
                "insertPostProductRequest",
                MediaType.APPLICATION_JSON_VALUE,
                jsonContent.getBytes()
        );

        // when
        mockMvc.perform(multipart("/api/products")
                        .file(requestPart) // JSON 데이터
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                // then
                .andExpect(status().isForbidden()) // 권한이 없으므로 403 상태 코드가 반환되어야 함
                .andDo(print());
    }


    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("상품 전체 조회")
    void selectAllProductTest() throws Exception {
        // given: 서비스 계층 동작 정의
        ProductWithBLOBs product = new ProductWithBLOBs();
        product.setId(11L);
        product.setCategoryId(1L);
        product.setProductStatusCodeId(1L);
        product.setName("test");
        product.setPrice(100);
        product.setExpirationDate(new Date());
        product.setSaleDate(new Date());
        product.setQuantity(20);
        ProductWithBLOBs product1 = new ProductWithBLOBs();
        product1.setId(22L);
        product1.setCategoryId(2L);
        product1.setProductStatusCodeId(1L);
        product1.setName("test1");
        product1.setPrice(500);
        product1.setExpirationDate(new Date());
        product1.setSaleDate(new Date());
        product1.setQuantity(100);

        List<ProductWithBLOBs> products = List.of(product, product1);
        when(productService.selectAllProduct()).thenReturn(products);

        // when & then: HTTP 상태 코드 확인 및 서비스 호출 검증
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 확인
                .andDo(print());

        // 서비스 계층 호출 검증
        verify(productService, times(1)).selectAllProduct();
    }

    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("특정 ID의 상품 조회")
    void selectByPrimaryKeyTest() throws Exception {
        // given
        ProductWithBLOBs product = new ProductWithBLOBs();
        product.setId(11L);
        product.setCategoryId(1L);
        product.setProductStatusCodeId(1L);
        product.setName("test");
        product.setPrice(100);
        product.setExpirationDate(new Date());
        product.setSaleDate(new Date());
        product.setQuantity(20);

        ProductWithBLOBs product1 = new ProductWithBLOBs();
        product1.setId(22L);
        product1.setCategoryId(2L);
        product1.setProductStatusCodeId(1L);
        product1.setName("test2");
        product1.setPrice(500);
        product1.setExpirationDate(new Date());
        product1.setSaleDate(new Date());
        product1.setQuantity(100);

        ProductWithBLOBs product2 = new ProductWithBLOBs();
        product2.setId(33L);
        product2.setCategoryId(3L);
        product2.setProductStatusCodeId(1L);
        product2.setName("test3");
        product2.setPrice(5000);
        product2.setExpirationDate(new Date());
        product2.setSaleDate(new Date());
        product2.setQuantity(500);

        // Mock: 서비스 계층의 동작을 정의
        List<Product> products = List.of(product, product1, product2);
        when(productService.selectByPrimaryKey(33L))
                .thenReturn((ProductWithBLOBs) products.stream().filter(p -> p.getId().equals(33L)).findFirst().orElse(null));

        // When & Then: MockMvc를 사용해 테스트
        mockMvc.perform(get("/api/products/33")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // 서비스 계층 호출 검증
        verify(productService, times(1)).selectByPrimaryKey(33L);
    }

    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("상품 정보 업데이트 성공 - 관리자용")
    void updateProductTest1() throws Exception {
        // Given
        Long productId = 123L;
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setId(productId);
        productWithBLOBs.setName("Updated Product");
        productWithBLOBs.setPrice(1200);
        productWithBLOBs.setCategoryId(4L);
        productWithBLOBs.setManufacturer("Updated Manufacturer");

        // Mocking Service
        Mockito.when(productService.updateByPrimaryKey(Mockito.any(ProductWithBLOBs.class))).thenReturn(1);

        // When & Then
        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productWithBLOBs)))
                .andExpect(status().isOk());

        // 서비스 계층 호출 검증
        verify(productService, times(1)).updateByPrimaryKey(productWithBLOBs);
    }

    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("상품 정보 업데이트 실패 - 상품 ID가 존재하지 않는 경우")
    void updateProductTest2() throws Exception {
        // Given
        Long productId = 123L;
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setId(productId);
        productWithBLOBs.setName("Updated Product");
        productWithBLOBs.setPrice(1200);
        productWithBLOBs.setCategoryId(4L);
        productWithBLOBs.setManufacturer("Updated Manufacturer");
        Long id = null;

        // Mocking Service
        Mockito.when(productService.updateByPrimaryKey(Mockito.any(ProductWithBLOBs.class)))
                .thenThrow(new RuntimeException("상품이 없습니다."));

        // When & Then
        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productWithBLOBs)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser()
    @DisplayName("상품 정보 업데이트 실패 - 일반 유저")
    void updateProductTest4() throws Exception {
        // Given
        Long productId = 123L;
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setId(productId);
        productWithBLOBs.setName("Updated Product");
        productWithBLOBs.setPrice(1200);
        productWithBLOBs.setCategoryId(4L);
        productWithBLOBs.setManufacturer("Updated Manufacturer");

        // Mocking Service
        Mockito.when(productService.updateByPrimaryKey(Mockito.any(ProductWithBLOBs.class))).thenReturn(1);

        // When & Then
        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productWithBLOBs)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("상품 삭제 성공 - 관리자")
    void deleteProductTest() throws Exception {
        Long productId = 1L;
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setId(productId);
        productWithBLOBs.setProductStatusCodeId(1L);
        productWithBLOBs.setName("세작");
        productWithBLOBs.setPrice(20000);
        productWithBLOBs.setCategoryId(7L);
        productWithBLOBs.setManufacturer("오설록농장");

        Mockito.when(productService.deleteByPrimaryKey(productId, 4L)).thenReturn(1);

        // When & Then
        mockMvc.perform(delete("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productWithBLOBs)))
                .andExpect(status().isOk());

        // 서비스 계층 호출 검증
        verify(productService, times(1)).deleteByPrimaryKey(productId, 4L);
    }

    @Test
    @WithMockCustomUser(role = "ADMIN")
    @DisplayName("상품 삭제 실패 - 관리자(상품이 없는 경우)")
    void deleteProductTest1() throws Exception {
        Long productId = 1L;
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setId(productId);
        productWithBLOBs.setProductStatusCodeId(1L);
        productWithBLOBs.setName("세작");
        productWithBLOBs.setPrice(20000);
        productWithBLOBs.setCategoryId(7L);
        productWithBLOBs.setManufacturer("오설록농장");

        Long id = null; // 존재하지 않는 상품 ID

        // 상품이 존재하지 않으면 0 또는 null을 반환하도록 설정
        Mockito.when(productService.deleteByPrimaryKey(id, 4L))
                .thenThrow(new RuntimeException("해당 ID의 상품이 존재하지 않습니다.")); // 0을 반환하면 상품이 존재하지 않음

        // When & Then: 상품이 존재하지 않으면 400 상태 코드와 함께 메시지를 반환하는지 확인
        mockMvc.perform(delete("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productWithBLOBs)))
                .andExpect(status().isNotFound()); // 상태 코드 400 (Bad Request)
    }

    @Test
    @WithMockCustomUser()
    @DisplayName("상품 삭제 실패 - 유저인 경우")
    void deleteProductTest4() throws Exception {
        Long productId = 1L;
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setId(productId);
        productWithBLOBs.setProductStatusCodeId(1L);
        productWithBLOBs.setName("세작");
        productWithBLOBs.setPrice(20000);
        productWithBLOBs.setCategoryId(7L);
        productWithBLOBs.setManufacturer("오설록농장");

        assertNotNull(productWithBLOBs);
        Mockito.when(productService.deleteByPrimaryKey(productId, 4L)).thenReturn(1);

        // When & Then
        mockMvc.perform(delete("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productWithBLOBs)))
                .andExpect(status().isForbidden());
    }

    @Nested
    @DisplayName("상품 검색 테스트")
    class searchProductTest{
        @Test
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        @DisplayName("상품 검색 (가격 설정X) 테스트")
        void searchByKeywordEmptyPriceTest() throws Exception {
            // given
            String keyword = "test";
            Integer minPrice = 100;
            Integer maxPrice = 20000;
            Integer orderBy = 2;

            List<ProductWithBLOBs> productList = new ArrayList<>();
            ProductWithBLOBs product = new ProductWithBLOBs();
            product.setName("Test Product");
            product.setPrice(15000);
            productList.add(product);

            // when
            when(productService.searchByKeyword(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(productList);

            // then
            mockMvc.perform(get("/api/products/test")
                            .param("keyword", keyword)
                            .param("minPrice", minPrice.toString())
                            .param("maxPrice", maxPrice.toString())
                            .param("orderBy", orderBy.toString())
                            .with(csrf()))  // CSRF 토큰 추가)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("Test Product"));

            verify(productService, times(1)).searchByKeyword(anyString(), anyInt(), anyInt(), anyInt());
        }

        @Test
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        @DisplayName("가격 설정 오류 테스트")
        void searchByKeywordPriceErrorTest() throws Exception {
            // given
            String keyword = "test";
            Integer minPrice = 20000;
            Integer maxPrice = 100;
            Integer orderBy = 2;

            // then
            mockMvc.perform(get("/api/products/test")
                            .param("keyword", keyword)
                            .param("minPrice", minPrice.toString())
                            .param("maxPrice", maxPrice.toString())
                            .param("orderBy", orderBy.toString()))
                    .andExpect(status().isBadRequest()) // 상태 코드 400 예상
                    .andExpect(content().string("가격 설정이 잘못 됐습니다.")); // 에러 메시지 확인
        }


        @Test
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        @DisplayName("키워드가 없는 경우 모든 상품 조회 테스트")
        void searchByKeywordEmptyKeywordTest() throws Exception {
            // given
            List<ProductWithBLOBs> productList = new ArrayList<>();
            ProductWithBLOBs product = new ProductWithBLOBs();
            product.setName("Test Product");
            product.setPrice(15000);
            productList.add(product);

            // when
            when(productService.selectAllProduct()).thenReturn(productList);

            // then
            mockMvc.perform(get("/api/products/test")
                            .param("keyword", "")
                            .param("minPrice", "100")
                            .param("maxPrice", "20000")
                            .param("orderBy", "1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("Test Product"));

            verify(productService, times(1)).selectAllProduct();
        }
    }
}