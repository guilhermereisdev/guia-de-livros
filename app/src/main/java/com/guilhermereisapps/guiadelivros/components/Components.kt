package com.guilhermereisapps.guiadelivros.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.guilhermereisapps.guiadelivros.model.ReaderBook
import com.guilhermereisapps.guiadelivros.navigation.ReaderScreens

@Preview
@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        text = "Guia de Livros",
        modifier = modifier,
        style = MaterialTheme.typography.displayMedium,
        color = Color.Red,
    )
}

@Preview
@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String> = mutableStateOf(""),
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction,
    )
}

@Preview
@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String> = mutableStateOf(""),
    labelId: String = "Senha",
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean> = mutableStateOf(false),
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    InputField(
        modifier = modifier,
        valueState = passwordState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Password,
        imeAction = imeAction,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisibility.value = !passwordVisibility.value },
            ) {
                Icon(Icons.Default.Close, contentDescription = null, tint = Color.Black)
            }
        },
        onAction = onAction,
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        keyboardActions = onAction,
    )
}

@Preview
@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    textId: String = "Enviar",
    loading: Boolean = false,
    validInputs: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}

@Preview
@Composable
fun MenuItem(
    text: String = "Menu",
    showMenu: MutableState<Boolean> = mutableStateOf(true),
    onClick: () -> Unit = {}
) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = text)
            }
        },
        onClick = {
            showMenu.value = false
            onClick()
        },
    )
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String = "Actual Screen",
    icon: ImageVector? = null,
    showProfile: Boolean = false,
    navController: NavController = rememberNavController(),
    onBackArrowClicked: () -> Unit = {}
) {
    val showMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "Profile icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .scale(1.2f)
                            .padding(end = 12.dp),
                    )
                }
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() },
                    )
                }
                Text(text = title)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Yellow),
        actions = {
            IconButton(onClick = { showMenu.value = !showMenu.value }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Menu")
            }
            DropdownMenu(expanded = showMenu.value, onDismissRequest = { showMenu.value = false }) {
                MenuItem(text = "Minha conta", showMenu = showMenu)
                MenuItem(text = "Estatísticas", showMenu = showMenu) {
                    navController.navigate(ReaderScreens.StatsScreen.name)
                }
                MenuItem(text = "Sair", showMenu = showMenu) {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderScreens.LoginScreen.name)
                    }
                }
            }
        },
    )
}

@Preview
@Composable
fun SingleBookCard(
    book: ReaderBook =
        ReaderBook("01", "Harry Potter e a Pedra Filosofal", "Aquela lá", "Menino bruxo"),
    navController: NavController = rememberNavController(),
    imageUrl: String =
        "http://books.google.com/books/content?id=aGMfCgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
    width: Dp? = null,
) {
    Card(
        modifier = Modifier
            .clickable { }
            .then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
            .height(150.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
//                    .aspectRatio(1f)
                    .width(100.dp)
//                    .clip(
//                        shape = RoundedCornerShape(
//                            topStart = 8.dp,
//                            bottomStart = 8.dp,
//                        )
//                    ),
                ,
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author(s): " + book.authors.toString(), overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodySmall
                )
                // TODO: adicionar mais campos aqui
            }
        }
    }
}
