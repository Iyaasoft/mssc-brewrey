package guru.springframework.msscbrewrey.web.model;

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
    @NotBlank
    private String beerStyle;
    @Positive
    @NotNull
    private long upc;
    @Null
    private OffsetDateTime createdDate;
    @Null
    private OffsetDateTime lastModifiedDate;
    @Null
    private Integer version;
    @Positive
    @NotNull
    private BigDecimal price;
    private Integer quantityOnHand;
    private Integer minOnHand;

}
