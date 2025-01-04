package hyper.darye.mvc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ForeignKeyMapper {
    @Update("SET FOREIGN_KEY_CHECKS = 0")
    void disableForeignKeyChecks();

    @Update("SET FOREIGN_KEY_CHECKS = 1")
    void enableForeignKeyChecks();
}
