package guru.springframework.msscbrewrey.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto implements Serializable {

    public static final long serialVersionUID = -5794999765990929309L;
    private UUID id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
}
