package hyper.darye.service;

import hyper.darye.dto.OrderMain;
import hyper.darye.dto.PointTransactionType;
import hyper.darye.mapper.MemberMapper;
import hyper.darye.mapper.OrderMainMapper;
import hyper.darye.mapper.PointTransactionTypeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PointServiceTest {
    @Autowired
    private PointService pointService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PointTransactionTypeMapper pttMapper;

    @Autowired
    private OrderMainMapper omMapper;



    @Test
    @DisplayName("포인트가 정상적으로 적립되는 경우")
    void UpdateTest() {
        // given
        // addPoint 변수 세팅
        Long memberId = 3L;
        String description = "Test Point Description";
        Integer amount = 10000;

        PointTransactionType pointTransactionType = new PointTransactionType();
        pointTransactionType = pttMapper.selectByPrimaryKey(1L);

        OrderMain orderMain = new OrderMain();
        orderMain.setMemberId(memberId);
        orderMain.setOrderDate(new Date());
        omMapper.insert(orderMain);

        int recentPoint = memberMapper.selectMemberById(memberId).getPoint();

        // when
        pointService.update(pointTransactionType, memberId, orderMain.getId(), description, amount);

        // then
        Integer point = (int)(amount * 0.01);
        Integer selectedPoint = memberMapper.selectMemberById(memberId).getPoint();
        assertEquals(point + recentPoint, selectedPoint);
    }

    @Test
    @DisplayName("포인트 사용 테스트")
    void UsePointTest(){
        // given
        // addPoint 변수 세팅
        Long memberId = 4L;
        String description = "Test Point Description";
        Integer usePoint = 10;
        Integer recentPoint = memberMapper.selectMemberById(memberId).getPoint();

        PointTransactionType pointTransactionType = new PointTransactionType();
        pointTransactionType = pttMapper.selectByPrimaryKey(1L);

        OrderMain orderMain = new OrderMain();
        orderMain.setMemberId(memberId);
        orderMain.setOrderDate(new Date());
        omMapper.insert(orderMain);

        // when
        pointService.usePoint(pointTransactionType, memberId, orderMain.getId(), description, usePoint);

        Integer selectedPoint = memberMapper.selectMemberById(memberId).getPoint();

        assertEquals(recentPoint - usePoint, selectedPoint);
    }
}
