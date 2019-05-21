package guru.springframework.api.v1.mapper;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    private static final long ID = 1L;
    private static final String NAME = "Fruits";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() throws Exception {
        //Given
        Category category = new Category(ID, NAME);

        //When
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //Then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}
