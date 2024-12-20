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
    }
}
