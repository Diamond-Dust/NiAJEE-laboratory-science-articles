package pl.edu.pg.eti.kask.labsart.avatar.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Avatar implements Serializable {
    final private UUID id = UUID.randomUUID();
    private byte[]     avatar;
}
