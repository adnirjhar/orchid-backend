package com.orchid.server.instance;

import com.orchid.server.domain.Instance;
import com.orchid.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstanceJPAService {

    @Autowired
    InstanceJPARepository instanceJPARepository;

    public List<Instance> fetchAllInstances() {
        List<Instance> instances = new ArrayList<Instance>();
        instanceJPARepository.findAll().forEach(instances::add);
        return instances;
    }

    public List<Instance> fetchAllInstancesByUser() {
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return instanceJPARepository.findInstancesByCreatedBy_Id(currentUser.getId());
    }

    public Instance fetchInstanceById(Integer id) {
        return instanceJPARepository.findInstanceById(id);
    }

    public Instance fetchInstanceByName(String instanceName) {
        return instanceJPARepository.findInstanceByName(instanceName);
    }

    public List<Instance> fetchInstancesByDialogFlowRefLike(String ref) {
        return instanceJPARepository.findInstancesByDialogFlowRefLike(ref);
    }

    public Instance fetchInstancesByTitle(String title) {
        return instanceJPARepository.findInstanceByTitle(title);
    }

    public List<Instance> fetchInstancesByCreatedBy(Long userId) {
        return instanceJPARepository.findInstancesByCreatedBy_Id(userId);
    }

    public Instance storeOrUpdateInstance(Instance instance) {

        User auth = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        instance.setCreatedBy(auth);
        return instanceJPARepository.save(instance);
    }

    public void deleteInstance(Instance instance) {
        instanceJPARepository.delete(instance);
    }
}
