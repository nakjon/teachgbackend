package com.techg.spring.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PagnationResponse {

	private List<?> content;
	private int pageNumber;
	private int pageSize ;
	private long totalElements;
	private int totalPages ;
	private boolean lastPage;
}
