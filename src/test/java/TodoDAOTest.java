import com.green.jdbcex.dao.TodoDAO;
import com.green.jdbcex.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TodoDAOTest {

    private TodoDAO todoDAO;


//    TodoDAOTest() {
//        todoDAO = new TodoDAO();
//    }

    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }


    @Test
    void testTime() throws Exception {
        System.out.println("현재 시각 : " + todoDAO.getTime());
    }


//    @Test
//    void insertTest() throws Exception {
//        TodoVO todoVO= TodoVO.builder().title("Sample Title").dueDate(LocalDate.of(2024,4,4)).build();
//
//        todoDAO.insert(todoVO);
//    }

    //교재 코드
    @Test
    public void testInsert() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...3")
                .dueDate(LocalDate.of(2021,12,31))
                .build();

        todoDAO.insert(todoVO);
    }


}