package jpabasic.start;

import javax.persistence.*;

@Entity
@Table
/*@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ",
        initialValue = 1, allocationSize = 1
)*/

/*@TableGenerator( //시퀀스 테이블을 만들어 시퀀스 관리 방법
        name = "BOARD_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnName = "BOARD_SEQ"
)*/
public class Board {
    @Id
    /*@SequenceGenerator( SequenceGenerator 는 필드 위에 생성가능
            name = "BOARD_SEQ_GENERATOR",
            sequenceName = "BOARD_SEQ",
            initialValue = 1
    )*/
    /*@GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")*/
    @GeneratedValue //create sequence hibernate_sequence start with 1 increment by 1 자동생성
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATA")
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
