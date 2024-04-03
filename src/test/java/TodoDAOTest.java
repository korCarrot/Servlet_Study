import com.green.jdbcex.dao.TodoDAO;
import com.green.jdbcex.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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

    @Test
    void testSelectAll() throws Exception{
        List<TodoVO> todoVOList =  todoDAO.selectAll();
//        System.out.println(todoVOList);
//        todoVOList.stream().forEach((x)->{System.out.println(x);});

        //1번글 조회 테스트
        todoVOList.stream().filter((x)->{return x.getTno()==1;}).forEach((x)->{System.out.println(x);});
    }

    @Test
    void testSelectOne() throws Exception{

        TodoVO todoVO=todoDAO.selectOne(1L);
        System.out.println(todoVO);
    }

    @Test
    void testDelete() throws Exception{
        todoDAO.delete(6L);
    }


    @Test
    void testModArticle() throws Exception{
        TodoVO todoVO = TodoVO.builder().tno(5L)
                .title("modArticleTest2")
                .dueDate(LocalDate.of(2024,04,01)).build();
        todoDAO.modArticle(todoVO);
    }
}