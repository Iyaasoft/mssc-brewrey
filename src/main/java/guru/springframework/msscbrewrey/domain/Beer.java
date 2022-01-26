package guru.springframework.msscbrewrey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length=36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdDate;
    @UpdateTimestamp
    private OffsetDateTime lastModifiedDate;
    private String beerName;
    private String beerStyle;
    @Column(unique = true)
    private Long upc;
    private BigDecimal price;
    private Integer quantityOnHand;
    private Integer minOnHand;
}
