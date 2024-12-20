package hyper.darye.service;

import hyper.darye.dto.OrderMain;
import hyper.darye.dto.PointTransaction;
import hyper.darye.dto.PointTransactionType;
import hyper.darye.mapper.MemberMapper;
import hyper.darye.mapper.OrderMainMapper;
import hyper.darye.mapper.PointTransactionMapper;
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

    @Autowired
    private PointTransactionMapper ptMapper;

    @Test
    @DisplayName("포인트 사용 취소, 반환 테스트")
    void cancelUsePointTest(){
      
    }

    @Test
    @DisplayName("포인트 사용 테스트")
    void UsePointTest(){
        // given
        // addPoint 변수 세팅
        Long memberId = 4L;
        String description = "Test Point Description";
        Integer cancelPoint = -1000;
        Integer recentPoint = memberMapper.selectMemberById(memberId).getPoint();

        PointTransactionType pointTransactionType = new PointTransactionType();
        pointTransactionType = pttMapper.selectByPrimaryKey(1L);

        OrderMain orderMain = new OrderMain();
        orderMain.setMemberId(memberId);
        orderMain.setOrderDate(new Date());
        omMapper.insert(orderMain);

        Long orderId = omMapper.selectByMemberId(memberId).getId();
        Integer usePoint = 10;
        Integer recentPoint = memberMapper.selectMemberById(memberId).getPoint();

        PointTransaction pt = new PointTransaction();
        pt.setMemberId(memberId);
        pt.setPointTransactionTypeId(1L);
        pt.setOrderMainId(orderId);
        pt.setAmount(cancelPoint);
        pt.setDescription(description);
        ptMapper.insertPointTransaction(pt);

        //when
        pointService.cancelUsePoint(pointTransactionType, memberId, orderMain.getId(), description);


        //then
        Integer selectedPoint = memberMapper.selectMemberById(memberId).getPoint();
        assertEquals(recentPoint + cancelPoint*-1, selectedPoint );
    }
}
