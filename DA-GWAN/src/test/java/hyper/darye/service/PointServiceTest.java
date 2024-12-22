package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.OrderMain;
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
    @DisplayName("포인트 적립 테스트")
    public void testAddPoint() {
        // Given
        Long memberId = (Long) 3L;
        String description = "포인트 적립";
        Long pttId = (Long) 1L;
        Integer amount = (Integer) 10000;
        PointTransactionType pointTransactionType = new PointTransactionType();
        pointTransactionType = pttMapper.selectByPrimaryKey(pttId);

        OrderMain om = new OrderMain();
        om.setMemberId(memberId);
        omMapper.insertSelective(om);
        Long omId = omMapper.selectByMemberId(memberId).getId();

        // 이전 포인트
        Integer lastPoint = memberMapper.selectMemberById(memberId).getPoint();

        // When
        pointService.addPoint(pointTransactionType, memberId, omId, description, amount);

        // 이후 포인트
        Integer currentPoint = memberMapper.selectMemberById(memberId).getPoint();

        // Then
        assertEquals(lastPoint + (int) (amount * 0.01), currentPoint);
    }

    @Test
    @DisplayName("포인트 조회 테스트")
    public void testSelectPoint() {
        // Given
        Long memberId = (Long) 3L;
        String description = "포인트 적립";
        Long pttId = (Long) 1L;
        Integer amount = (Integer) 10000;
        PointTransactionType pointTransactionType = new PointTransactionType();
        pointTransactionType = pttMapper.selectByPrimaryKey(pttId);

        OrderMain om = new OrderMain();
        om.setMemberId(memberId);
        omMapper.insertSelective(om);
        Long omId = omMapper.selectByMemberId(memberId).getId();

        // When
        pointService.addPoint(pointTransactionType, memberId, omId, description, amount);

        // 이후 포인트
        Integer currentPoint = memberMapper.selectMemberById(memberId).getPoint();

        // then
        Member member = pointService.selectPoint(memberId);
        assertEquals(member.getPoint(), currentPoint);
    }

    @Test
    @DisplayName("포인트 사용 테스트")
    public void testUsePoint() {
        // Given
        Long memberId = (Long) 3L;
        String description = "포인트 적립";
        Long pttId = (Long) 1L;
        Integer amount = (Integer) (memberMapper.selectMemberById(memberId).getPoint() - 10);
        PointTransactionType pointTransactionType = new PointTransactionType();
        pointTransactionType = pttMapper.selectByPrimaryKey(pttId);

        OrderMain om = new OrderMain();
        om.setMemberId(memberId);
        omMapper.insertSelective(om);
        Long omId = omMapper.selectByMemberId(memberId).getId();

        // 이전 포인트
        Integer lastPoint = memberMapper.selectMemberById(memberId).getPoint();

        // When
        pointService.usePoint(pointTransactionType, memberId, omId, description, amount);

        // 이후 포인트
        Integer currentPoint = memberMapper.selectMemberById(memberId).getPoint();

        assertEquals(lastPoint - amount, currentPoint);
    }

    @Test
    @DisplayName("포인트 사용 취소, 반환 테스트")
    public void testCancelUsePoint() {
        // 포인트 사용 이력 테스트
        // Given
        Long memberId = (Long) 3L;
        String description = "포인트 적립";
        Long pttId = (Long) 1L;
        Integer amount = (Integer) (memberMapper.selectMemberById(memberId).getPoint() - 10);
        PointTransactionType pointTransactionType = new PointTransactionType();
        pointTransactionType = pttMapper.selectByPrimaryKey(pttId);

        OrderMain om = new OrderMain();
        om.setMemberId(memberId);
        omMapper.insertSelective(om);
        Long omId = omMapper.selectByMemberId(memberId).getId();

        // 이전 포인트
        Integer lastPoint = memberMapper.selectMemberById(memberId).getPoint();

        // When
        pointService.usePoint(pointTransactionType, memberId, omId, description, amount);

        // 이후 포인트
        Integer currentPoint = memberMapper.selectMemberById(memberId).getPoint();

        assertEquals(lastPoint - amount, currentPoint);

        // Given
        String cancleDescription = "포인트 사용 취소";


        // When
        pointService.cancelUsePoint(pointTransactionType, memberId, omId, cancleDescription);

        // Then
        // 사용취소한 포인트
        Integer cancelPoint = memberMapper.selectMemberById(memberId).getPoint();
        assertEquals(lastPoint, cancelPoint); // 사용이전 포인트와 사용취소한 포인트는 같다
 }
}
