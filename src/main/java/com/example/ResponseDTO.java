package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder //디자인 패턴중 하나. 빌더 패턴을 사용해 오브젝트를 생성할 수 있다.
@NoArgsConstructor //매개변수가 없는 생성자를 구현해준다.
@AllArgsConstructor //모든 멤버변수를 매개변수로 받는 생성자를 구현해준다.
@Data //게터, 세터를 구현해준다.
public class ResponseDTO<T> {
    private String error;
    private List<T> data;
}
