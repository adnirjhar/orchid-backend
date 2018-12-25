package com.orchid.server.instance;

import com.orchid.server.domain.Instance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstanceJPARepository extends CrudRepository<Instance, Integer> {

    public Instance findInstanceById(Integer id);

    public Instance findInstanceByName(String name);

    public Instance findInstanceByTitle(String title);

    public List<Instance> findInstancesByDialogFlowRefLike(String ref);

    public List<Instance> findInstancesByCreatedBy_Id(Long id);

}
