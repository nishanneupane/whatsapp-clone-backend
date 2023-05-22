package com.nishanneupane.whatsapp.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupchatRequest{
	private List<Integer> userIds;
	private String chat_name;
	private String chat_image;

}
