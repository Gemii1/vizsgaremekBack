package hu.fitness.service;

import hu.fitness.converter.BlogConverter;
import hu.fitness.domain.Blog;
import hu.fitness.domain.Trainer;
import hu.fitness.dto.BlogList;
import hu.fitness.dto.BlogRead;
import hu.fitness.dto.BlogSave;
import hu.fitness.exception.BlogNotFoundException;
import hu.fitness.exception.TrainerNotFoundException;
import hu.fitness.repository.BlogRepository;
import hu.fitness.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    public List<BlogList> listBlogs() {
        List<BlogList> blogList = new ArrayList<>();
        List<Blog> blogs = blogRepository.findAll();
        blogList = BlogConverter.convertModelsToList(blogs);
        return blogList;
    }

    public BlogRead readBlogById(Integer id){
        if(!blogRepository.existsById(id)){
            throw new BlogNotFoundException();
        }
        Blog blog = blogRepository.getReferenceById(id);
        return BlogConverter.convertModelToRead(blog);
    }

    public BlogRead createBlog(BlogSave blogSave){
        if(!trainerRepository.existsById(blogSave.getTrainerId())){
            throw new TrainerNotFoundException();
        }
        Trainer trainer = trainerRepository.getReferenceById(blogSave.getTrainerId());
        Blog blog = BlogConverter.convertSaveToModel(blogSave,trainer);
        Blog savedBlog = blogRepository.save(blog);
        return BlogConverter.convertModelToRead(savedBlog);
    }

    public BlogRead deleteBlog(int id){
        if(!blogRepository.existsById(id)){
            throw new BlogNotFoundException();
        }

        BlogRead blogRead = BlogConverter.convertModelToRead(blogRepository.getReferenceById(id));
        blogRepository.deleteById(id);
        return blogRead;
    }

    public BlogRead updateBlog(int id, BlogSave blogSave){
        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException();
        }
        if (!trainerRepository.existsById(blogSave.getTrainerId())) {
            throw new TrainerNotFoundException();
        }
        Trainer trainer = trainerRepository.getReferenceById(blogSave.getTrainerId());
        Blog blog = BlogConverter.convertSaveToModel(blogSave,trainer);
        blog.setId(id);
        blogRepository.save(blog);
        return BlogConverter.convertModelToRead(blog);
    }

}
