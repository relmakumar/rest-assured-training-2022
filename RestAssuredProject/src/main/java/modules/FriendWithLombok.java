package modules;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendWithLombok {
	
	private String firstname;
	private String lastname;
	private int id;
	private int age;
	private List<AddressWithLombok> address;

}
