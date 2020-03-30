import com.xuecheng.auth.UcenterAuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UcenterAuthApplication.class)
@RunWith(SpringRunner.class)
public class TestPassword {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        String xcWebApp = passwordEncoder.encode("itcast");
        System.out.println(xcWebApp);
        boolean xcWebApp1 = passwordEncoder.matches("itcast", "$2a$10$b7AHgutBiHgqYyL8yxD.b.phdwdlQrBE1DMhFRL763w4BfksxcNq.");
        System.out.println(xcWebApp1);
        boolean equals = xcWebApp.equals("$2a$10$9bEpZ/hWRQxyr5hn5wHUj.jxFpIrnOmBcWlE/g/0Zp3uNxt9QTh/S");
        System.out.println(equals);
    }
}
