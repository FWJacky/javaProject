package com.liu.cashsystem.cmd;

import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.CustomerCommand;
import com.liu.cashsystem.cmd.annotation.EntranceCommand;
import com.liu.cashsystem.cmd.impl.account.AccountBrowseCommand;
import com.liu.cashsystem.cmd.impl.account.AccountPasswordResetCommand;
import com.liu.cashsystem.cmd.impl.account.AccountStatusSetCommand;
import com.liu.cashsystem.cmd.impl.common.AboutCommand;
import com.liu.cashsystem.cmd.impl.common.HelpCommand;
import com.liu.cashsystem.cmd.impl.common.QuitCommand;
import com.liu.cashsystem.cmd.impl.entrance.LoginCommand;
import com.liu.cashsystem.cmd.impl.entrance.RegisterCommand;
import com.liu.cashsystem.cmd.impl.goods.GoodsBrowseCommand;
import com.liu.cashsystem.cmd.impl.goods.GoodsPutAwayCommand;
import com.liu.cashsystem.cmd.impl.goods.GoodsSoldOutCommand;
import com.liu.cashsystem.cmd.impl.goods.GoodsUpdateCommand;
import com.liu.cashsystem.cmd.impl.order.OrderBrowseCommand;
import com.liu.cashsystem.cmd.impl.order.OrderPayCommand;

import java.util.*;

/**
 * @ClassName Commands
 * @Description TODO  对命令进行分类
 * @Author L
 * @Date 2019/8/4 12:23
 * @Version 1.0
 **/

public class Commands {

    public static final Map<String, Command> ADMIN_COMMANDS = new HashMap<>();
    public static final Map<String, Command> CUSTOMER_COMMANDS = new HashMap<>();
    public static final Map<String, Command> ENTRANCE_COMMANDS = new HashMap<>();

    // 用来存放所有的命令的集合
    private static final Set<Command> COMMANDS = new HashSet<>();
    private static final Command CACHED_HELP_COMMANDS;

    // 在程序执行前，就将命令分好类
    static {
        Collections.addAll(COMMANDS,
                new AccountBrowseCommand(),
                new AccountPasswordResetCommand(),
                new AccountStatusSetCommand(),
                new AboutCommand(),
                // 将HelpCommand 进行缓存
                CACHED_HELP_COMMANDS = new HelpCommand(),
                new QuitCommand(),
                new LoginCommand(),
                new RegisterCommand(),
                new GoodsSoldOutCommand(),
                new GoodsUpdateCommand(),
                new GoodsBrowseCommand(),
                new GoodsPutAwayCommand(),
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

            // 查看commandMeta是否为空以及name的值
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

    // 从用户的输入中找到对于的对象  比如用户输入了DL，则得到对于的对象
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

        // 遍历相应的Map，根据commandKey，得到对应的value值---即命令
        return commandMap.getOrDefault(commandKey,getCachedHelpCommands());
    }
}

