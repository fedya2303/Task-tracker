package by.bntu.backend.dto;

import by.bntu.backend.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {

    private Long id;

    private String name;

    private String description;

    private String status;
}
