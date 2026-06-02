package com.demo.project87.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 45)
    private String seatNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;
    @Size(max = 45)
    private String bookedBy;
    @Size(max = 45)
    private String lockedBy;
    //Never expose TTL over json.
    @JsonIgnore
    private LocalDateTime lockExpiry;
    @Column(nullable = false)
    @Builder.Default
    private Boolean booked = false;
    @Column(nullable = false)
    @Builder.Default
    private Boolean entered = false;
    private String entryToken;
    private Double price;
    @Lob
    @Column(columnDefinition = "BYTEA")
    @JsonIgnore
    @ToString.Exclude
    private byte[] qrCode;
    @Version
    private int version;

}
