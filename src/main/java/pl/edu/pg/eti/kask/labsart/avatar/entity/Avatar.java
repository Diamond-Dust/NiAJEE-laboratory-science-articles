package pl.edu.pg.eti.kask.labsart.avatar.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "avatars")
public class Avatar implements Serializable {

    @Id
    final private UUID id = UUID.randomUUID();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[]     avatar;

    @OneToOne(mappedBy = "avatar")
    private Scientist scientist;
}
