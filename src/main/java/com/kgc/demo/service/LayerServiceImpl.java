package com.kgc.demo.service;

import com.kgc.demo.Model.Layer;
import com.kgc.demo.dao.LayerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("layerService")
@Transactional
public class LayerServiceImpl implements LayerService{

    @Resource
    private LayerMapper layerMapper;


    @Override
    public int InsertLayer(Layer layer) {
        return layerMapper.insert(layer);
    }


}
