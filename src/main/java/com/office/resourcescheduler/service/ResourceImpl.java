package com.office.resourcescheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.office.resourcescheduler.config.SpringContext;
import com.office.resourcescheduler.model.Resource;
import com.office.resourcescheduler.repository.ResourceRepository;

@Service
@Transactional
public class ResourceImpl {

	private ResourceRepository resourceRepository;

	public static ResourceImpl getInstance() {
		return SpringContext.getBean(ResourceImpl.class);
	}

	@Autowired
	public ResourceImpl(ResourceRepository resourceRepository) {
		this.resourceRepository = resourceRepository;
	}

	public List<Resource> findAll() {
		return resourceRepository.findAll();
	}

	public Resource save(Resource resource) {
		return resourceRepository.save(resource);
	}

	public Optional<Resource> findById(Long resourceId) {
		return resourceRepository.findById(resourceId);
	}

	public void deleteById(Long resourceId) {
		resourceRepository.deleteById(resourceId);
	}
}
