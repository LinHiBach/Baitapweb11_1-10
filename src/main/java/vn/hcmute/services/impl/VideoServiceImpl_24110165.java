package vn.hcmute.services.impl;

import java.util.List;
import vn.hcmute.dao.IVideoDao_24110165;
import vn.hcmute.dao.impl.VideoDaoImpl_24110165;
import vn.hcmute.entity.Video_24110165;
import vn.hcmute.services.IVideoService_24110165;

public class VideoServiceImpl_24110165 implements IVideoService_24110165 {
    private final IVideoDao_24110165 videoDao = new VideoDaoImpl_24110165();

    @Override
    public void insert(Video_24110165 video) {
        videoDao.insert(video);
    }

    @Override
    public void update(Video_24110165 video) {
        videoDao.update(video);
    }

    @Override
    public void delete(String id) {
        videoDao.delete(id);
    }

    @Override
    public Video_24110165 findById(String id) {
        return videoDao.findById(id);
    }

    @Override
    public List<Video_24110165> findAll() {
        return videoDao.findAll();
    }

    @Override
    public List<Video_24110165> findByTitle(String title) {
        return videoDao.findByTitle(title);
    }

    @Override
    public List<Video_24110165> findAll(int page, int pagesize) {
        return videoDao.findAll(page, pagesize);
    }

    @Override
    public List<Video_24110165> findByCategoryId(int categoryId, int page, int pagesize) {
        return videoDao.findByCategoryId(categoryId, page, pagesize);
    }

    @Override
    public int countByCategoryId(int categoryId) { return videoDao.countByCategoryId(categoryId); }

    @Override
    public long countFavorites(String videoId) { return videoDao.countFavorites(videoId); }

    @Override
    public long countShares(String videoId) { return videoDao.countShares(videoId); }

    @Override
    public int count() {
        return videoDao.count();
    }
}
