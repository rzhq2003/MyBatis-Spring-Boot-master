package tk.mybatis.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.mapper.RightsMapper;
import tk.mybatis.springboot.model.Rights;
import tk.mybatis.springboot.util.BaseService;

@Service
public class RightsService extends BaseService<Rights> {
	
	@Autowired
	RightsMapper rightsMapper;
	

}
