package guru.springframework.msscbrewrey.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

    @Null
    private UUID id;
    @NotBlank
    private String beerName;
    @NotNull
    private BearStyleEnum beerStyle;
    @NotNull
    private String upc;
    @Null
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "\"yyyy-MM-dd'T'HH:mm:ssZ\"")
    private OffsetDateTime createdDate;
    @Null
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "\"yyyy-MM-dd'T'HH:mm:ssZ\"")
    private OffsetDateTime lastModifiedDate;
    @Null
    private Integer version;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Positive
    @NotNull
    private BigDecimal price;
    @Positive
    private Integer quantityOnHand;
    @Positive
    private Integer minOnHand;

}
