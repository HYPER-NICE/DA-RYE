package hyper.darye.service;

import hyper.darye.model.entity.PointTransaction;
import hyper.darye.model.entity.PointTransactionType;
import hyper.darye.mapper.*;
import hyper.darye.mapper.order.OrderMainMapper;
import hyper.darye.mapper.order.payment.OrderPaymentMainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class PointServiceTest {

    @InjectMocks
    PointService pointService;

    @Mock
    MemberMapper memberMapper;

    @Mock
    PointTransactionMapper ptMapper;

    @Mock
    PointTransactionTypeMapper pttMapper;

    @Mock
    OrderPaymentMainMapper opmMapper;

    @Mock
    OrderMainMapper omMapper;

    @Mock
    PointTransactionType pointTransactionType;

    private Long memberId = 1L;
    private Long orderMainId = 100L;
    private Integer amount = 5000;
    private String description = "Order Points";

    @BeforeEach
    void setUp() {
        // 초기화 작업
        pointTransactionType = mock(PointTransactionType.class);
        when(pointTransactionType.getId()).thenReturn(1L);
    }

    @Test
    @DisplayName("포인트 적립 테스트")
    void testAddPoint() {
        // given
        when(memberMapper.addPoint(memberId, (int)(amount * 0.01))).thenReturn(1);

        // when
        pointService.addPoint(pointTransactionType, memberId, orderMainId, description, amount);

        // then
        verify(memberMapper, times(1)).addPoint(memberId, (int)(amount * 0.01));
        verify(ptMapper, times(1)).insertPointTransaction(any(PointTransaction.class));
    }

    @Test
    @DisplayName("포인트 사용 테스트")
    void testUsePoint() {
        // given
        int pointToUse = 500;

        // when
        pointService.usePoint(pointTransactionType, memberId, orderMainId, description, pointToUse);

        // then
        verify(memberMapper, times(1)).usePoint(memberId, pointToUse);
        verify(ptMapper, times(1)).insertPointTransaction(any(PointTransaction.class));
    }

    @Test
    @DisplayName("포인트 사용 취소 테스트")
    void testCancelUsePoint() {
        // given
        PointTransaction pointTransaction = new PointTransaction();
        pointTransaction.setAmount(-100);  // 예시로 -100 포인트로 설정
        when(ptMapper.selectByMemberIdAndOrderId(memberId, orderMainId)).thenReturn(pointTransaction);
        when(memberMapper.addPoint(memberId, 100)).thenReturn(1);  // 취소된 포인트를 되돌림

        // when
        pointService.cancelUsePoint(pointTransactionType, memberId, orderMainId, description);

        // then
        verify(memberMapper, times(1)).addPoint(memberId, 100);
        verify(ptMapper, times(1)).insertPointTransaction(any(PointTransaction.class));
    }

}