package first.first.Service.Imp;

import first.first.Entity.Inform;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.InformTypeEnum;
import first.first.Repository.InformRepository;
import first.first.Service.InformService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/9/24 9:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class InfromServiceImpTest {
    @Autowired
    InformService informService;
    @Autowired
    InformRepository informRepository;

    @Test
    public void findAll() {
    }

    @Test
    public void findByInformId() {
    }

    @Test
    public void save() {


    }
}