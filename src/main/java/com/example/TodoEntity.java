package com.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder //디자인 패턴중 하나. 빌더 패턴을 사용해 오브젝트를 생성할 수 있다.
@NoArgsConstructor //매개변수가 없는 생성자를 구현해준다.
@AllArgsConstructor //모든 멤버변수를 매개변수로 받는 생성자를 구현해준다.
@Data //게터, 세터를 구현해준다.
@Entity //자바 클래스를 엔티티로 지정하기 위함
@Table(name = "Todo") //이 엔티티는 DB의 Todo테이블에 매핑된다.
public class TodoEntity {
    @Id //기본키가 될 필드
    @GeneratedValue(generator = "system-uuid") //id를 자동으로 생성하기 위함.
    @GenericGenerator(name="system-uuid", strategy = "uuid") //id를 자동으로 생성하기 위함.
    private String id; //이 오브젝트의 아이디
    private String userId; //이 오브젝트를 생성한 유저의 아이디
    private String title; //Todo 타이틀
    private boolean done; //true - todo를 완료한 경우.
}
