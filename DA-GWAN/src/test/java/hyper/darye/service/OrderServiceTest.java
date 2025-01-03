package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.Product;
import hyper.darye.mapper.MemberMapper;
import hyper.darye.mapper.ProductMapper;
import hyper.darye.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testAdd() {
        // given: 테스트 데이터 준비
        Member member = memberMapper.selectByPrimaryKey(1L);
        List<Product> products = productMapper.selectAll();

        // when: 서비스 메서드 호출
        int insertedRows = orderService.add(member.getId(), products);

        // then: 결과 검증
        assertEquals(products.size(), insertedRows, "삽입된 행 수가 제품 수와 일치해야 합니다.");
    }
}
