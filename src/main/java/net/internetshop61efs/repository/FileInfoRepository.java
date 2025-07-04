package net.internetshop61efs.repository;

import net.internetshop61efs.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
