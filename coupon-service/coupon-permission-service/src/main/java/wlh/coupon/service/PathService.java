package wlh.coupon.service;

import wlh.coupon.dao.PathRepository;
import wlh.coupon.entity.Path;
import wlh.coupon.vo.CreatePathRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Realization of path-related service functions
 */
@Slf4j
@Service
public class PathService {

    /** Path Repository */
    private final PathRepository pathRepository;

    @Autowired
    public PathService(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    /**
     * Add a new path to the data table
     * @param request {@link CreatePathRequest}
     * @return Path primary key of the data record
     * */
    public List<Integer> createPath(CreatePathRequest request) {

        List<CreatePathRequest.PathInfo> pathInfos = request.getPathInfos();
        List<CreatePathRequest.PathInfo> validRequests =
                new ArrayList<>(request.getPathInfos().size());
        List<Path> currentPaths = pathRepository.findAllByServiceName(
                pathInfos.get(0).getServiceName()
        );

        if (!CollectionUtils.isEmpty(currentPaths)) {

            for (CreatePathRequest.PathInfo pathInfo : pathInfos) {
                boolean isValid = true;

                for (Path currentPath : currentPaths) {
                    if (currentPath.getPathPattern()
                            .equals(pathInfo.getPathPattern()) &&
                    currentPath.getHttpMethod().equals(pathInfo.getHttpMethod())) {
                        isValid = false;
                        break;
                    }
                }

                if (isValid) {
                    validRequests.add(pathInfo);
                }
            }
        } else {
            validRequests = pathInfos;
        }

        List<Path> paths = new ArrayList<>(validRequests.size());
        validRequests.forEach(p -> paths.add(new Path(
                p.getPathPattern(),
                p.getHttpMethod(),
                p.getPathName(),
                p.getServiceName(),
                p.getOperationMode()
        )));

        return pathRepository.saveAll(paths)
                .stream().map(Path::getId).collect(Collectors.toList());
    }
}
