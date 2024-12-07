package hyper.darye.dto;

public class SelectMemberDto {
    private Long id;
    private String name;

    public SelectMemberDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
