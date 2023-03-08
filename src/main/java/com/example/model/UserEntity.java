package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data //게터, 세터를 구현해준다.
@Entity //자바 클래스를 엔티티로 지정하기 위함
@Builder //디자인 패턴중 하나. 빌더 패턴을 사용해 오브젝트를 생성할 수 있다.
@NoArgsConstructor //매개변수가 없는 생성자를 구현해준다.
@AllArgsConstructor //모든 멤버변수를 매개변수로 받는 생성자를 구현해준다.
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")}) //이 엔티티는 DB의 ~~테이블에 매핑된다.
public class UserEntity {
    @Id //기본키가 될 필드
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id; // 유저에게 고유하게 부여되는 id.

    @Column(nullable = false)
    private String username;
    private String password; //이후 SSO를 구현하므로 null값 입력 가능.
    private String role; //사용자의 롤. 어드민, 일반 사용자 등등
    private String authProvider; //이후 0Auth에서 사용할 유저 정보 제공자: github
}
