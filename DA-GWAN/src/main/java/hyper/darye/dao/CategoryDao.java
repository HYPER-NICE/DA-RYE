package hyper.darye.dao;

import hyper.darye.model.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao {

    // 모든 카테고리 조회
    List<Category> selectAll();

    // ID를 기준으로 카테고리 조회
    Category selectCategoryById(@Param("id") Long id);

    // 이름을 기준으로 카테고리 조회
    Category selectCategoryByName(@Param("name") String name);

    // 특정 카테고리의 직계 하위 카테고리 조회
    List<Category> selectDirectSubCategoriesById(@Param("parentId") Long parentId);

    // 특정 카테고리의 전체 하위 카테고리 조회
    List<Category> selectAllSubCategories(@Param("parentId") Long parentId);

    // **이름**을 기준으로 특정 카테고리를 부모로 가지는 직계 하위 카테고리 조회
    List<Category> selectDirectSubCategoriesByName(@Param("parentName") String parentName);

    // **이름**을 기준으로 특정 카테고리를 부모로 가지는 전체 하위 카테고리 조회
    List<Category> selectAllSubCategoriesByName(@Param("parentName") String parentName);

    // 새로운 카테고리 추가
    int insertCategory(Category Category);

    // 카테고리 삭제
    void deleteCategory(@Param("id") Long id);

    // 카테고리 정보 업데이트
    void updateCategory(Category Category);
}
