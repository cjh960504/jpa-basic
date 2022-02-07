package compositeKey.Identifying.onetoone;

import javax.persistence.*;

@Entity
public class BoardDetail {

    @Id
    private Long boardId;

    /* @MapsId 는 EmbeddedId 기본 키 내의 속성 또는 상위 엔터티의 단순 기본 키에 대한 매핑을 제공하는 ManyToOne 또는 OneToOne 관계 속성을 지정합니다. */
    @MapsId // @MapsId는 @Id를 사용해서 식별자로 지정한 BoardDetail.boardId와 매핑된다.
    @OneToOne
    @JoinColumn(name="BOARD_ID")
    private Board board;

    private String content;

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
