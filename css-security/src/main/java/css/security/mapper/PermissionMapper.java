package css.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashSet;

@Mapper
public interface PermissionMapper {

    HashSet<String> selectAllKeywords();

}
