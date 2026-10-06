package vn.hcmute.dao;

import java.util.List;
import vn.hcmute.entity.Video_24110165;

public interface IVideoDao_24110165 {
    void insert(Video_24110165 video);
    void update(Video_24110165 video);
    void delete(String id);
    Video_24110165 findById(String id);
    List<Video_24110165> findAll();
    List<Video_24110165> findByTitle(String title);
    List<Video_24110165> findAll(int page, int pagesize);
    List<Video_24110165> findByCategoryId(int categoryId, int page, int pagesize);
    int countByCategoryId(int categoryId);
    long countFavorites(String videoId);
    long countShares(String videoId);
    int count();
}
