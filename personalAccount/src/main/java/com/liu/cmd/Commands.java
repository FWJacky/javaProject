package com.liu.cmd;

import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.annotation.EntranceCommand;
import com.liu.cmd.impl.account.*;
import com.liu.cmd.impl.common.AboutCommand;
import com.liu.cmd.impl.common.HelpCommand;
import com.liu.cmd.impl.common.QuitCommand;
import com.liu.cmd.impl.entrance.LoginCommand;
import com.liu.cmd.impl.entrance.RegisterCommand;
import com.liu.cmd.impl.goods.GoodsBrowseCommand;
import com.liu.cmd.impl.goods.GoodsPutAwayCommand;
import com.liu.cmd.impl.goods.GoodsSoldOutCommand;
import com.liu.cmd.impl.goods.GoodsUpdateCommand;
import com.liu.cmd.impl.order.OrderBrowseCommand;
import com.liu.cmd.impl.order.OrderPayCommand;

import java.util.*;

/**
 * @ClassName Commands
 * @Description TODO  对命令进行分组
 * @Author L
 * @Date 2019/8/22 15:33
 * @Version 1.0
 **/

/*
 *  命令分为三组，分别为：管理员命令、客户端命令、入口命令
 *
 **/
public class Commands {

    // 管理员命令
    public static final Map<String, Command> ADMIN_COMMANDS = new HashMap<>();

    // 客户端命令
    public static final Map<String, Command> CUSTOMER_COMMANDS = new HashMap<>();

    // 入口命令
    public static final Map<String, Command> ENTRANCE_COMMANDS = new HashMap<>();

    // 用来存放所有的命令的集合
    private static final Set<Command> COMMANDS = new HashSet<>();

    // 缓存命令
    // 由于在每次进行业务时，都需要提供帮助信息，所以将帮助信息放入到缓存命令中
    private static final Command CACHED_HELP_COMMANDS;

    // 在程序执行前，将命令分好组
    static {
        // 先添加所有的命令
        Collections.addAll(COMMANDS,
                new AccountBrowseCommand(),
                new AccountInfoCommand(),
                new AccountModifyPasswordCommand(),
                new AccountPasswordResetCommand(),
                new AccountStatusCommand(),
                new AboutCommand(),
                CACHED_HELP_COMMANDS = new HelpCommand(),
                new QuitCommand(),
                new LoginCommand(),
                new RegisterCommand(),
                new GoodsBrowseCommand(),
                new GoodsPutAwayCommand(),
                new GoodsSoldOutCommand(),
                new GoodsUpdateCommand(),
                new OrderBrowseCommand(),
                new OrderPayCommand()
        );

        // 对命令进行分类
        for (Command command : COMMANDS) {
            Class<?> cls = command.getClass();
            // 获得adminCommand的注解
            AdminCommand adminCommand = cls.getDeclaredAnnotation(AdminCommand.class);
            CustomerCommand customerCommand = cls.getDeclaredAnnotation(CustomerCommand.class);
            EntranceCommand entranceCommand = cls.getDeclaredAnnotation(EntranceCommand.class);
            CommandMeta commandMeta = cls.getDeclaredAnnotation(CommandMeta.class);

            if(commandMeta == null) {
                continue;
            }

            String commandKey = commandMeta.name();

            if(adminCommand != null) {
                ADMIN_COMMANDS.put(commandKey,command);
            }
            if(customerCommand != null) {
                CUSTOMER_COMMANDS.put(commandKey,command);
            }
            if(entranceCommand != null) {
                ENTRANCE_COMMANDS.put(commandKey,command);
            }
        }
    }

    // 得到缓存的命令 ---- HelpCommand命令
    public static Command getCachedHelpCommands() {
        return CACHED_HELP_COMMANDS;
    }

    // 从用户的输入中找到对应的对象，例如用户输入dl，则对应入口命令对象
    public static Command getAdminCommand(String commandKey) {
        return getCommand(commandKey,ADMIN_COMMANDS);
    }

    public static Command getCustomerCommand(String commandKey) {
        return getCommand(commandKey,CUSTOMER_COMMANDS);
    }

    public static Command getEntranceCommand(String commandKey) {
        return getCommand(commandKey,ENTRANCE_COMMANDS);
    }


    public static Command getCommand(String commandKey,Map<String,Command> commandMap) {
        // 遍历相应的Map，根据commandKey，得到相应的value--即命令
        return commandMap.getOrDefault(commandKey,getCachedHelpCommands());
    }
}
