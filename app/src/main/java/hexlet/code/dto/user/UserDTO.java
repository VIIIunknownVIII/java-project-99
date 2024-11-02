package hexlet.code.dto.user;

import java.time.LocalDate;

public class UserDTO {

    private Long id;
    private String email;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    // Геттеры и сеттеры для id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Геттеры и сеттеры для email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Геттеры и сеттеры для createdAt
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры для updatedAt
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
