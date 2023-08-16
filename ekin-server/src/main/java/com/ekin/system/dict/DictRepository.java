//package com.ekin.system.dict;
//
//import com.cartisan.repository.BaseRepository;
//import com.ekin.system.dict.domain.Dict;
//
//import java.util.Optional;
//
///**
// * @author colin
// */
//public interface DictRepository extends BaseRepository<Dict, Long> {
//    /**
//     * 根据字典Code获取字典
//     * @param code 字典Code
//     * @return Code对应的字典
//     */
//    Optional<Dict> findByCode(String code);
//
//    /**
//     * 指定的字典Code是否存在
//     * @param code 字典Code
//     * @return true: 存在 false：不存在
//     */
//    boolean existsByCode(String code);
//
//    /**
//     * 指定的字典Code是否存在（排队指定的Id）
//     * @param code 字典Code
//     * @param id 字典Id
//     * @return true: 存在 false：不存在
//     */
//    boolean existsByCodeAndIdNot(String code, Long id);
//}
