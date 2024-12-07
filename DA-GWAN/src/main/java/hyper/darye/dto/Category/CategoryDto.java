package hyper.darye.dto.Category;

import hyper.darye.dto.BaseDto;

import java.util.Objects;

/**
 * MyBatis 전용 데이터 전송 객체(DTO)로, 카테고리 데이터를 표현합니다.
 */
public class CategoryDto extends BaseDto {

    private String name; // 카테고리 이름
    private Long parentId; // 부모 카테고리 ID

    // 기본 생성자
    public CategoryDto() {
    }

    // 모든 필드를 포함한 생성자
    public CategoryDto(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + super.id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                super.systemDatetoString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDto)) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(super.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.id, this.name, this.parentId);
    }
}
