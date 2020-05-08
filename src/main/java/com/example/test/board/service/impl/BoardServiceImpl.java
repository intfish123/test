package com.example.test.board.service.impl;

import com.example.test.board.entity.Board;
import com.example.test.board.mapper.BoardMapper;
import com.example.test.board.service.IBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangxiaolei
 * @since 2020-04-16
 */
@Service
public class BoardServiceImpl extends ServiceImpl<BoardMapper, Board> implements IBoardService {

}
