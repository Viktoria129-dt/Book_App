package com.example.bookapp.presentation.BookList.components

import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookapp.domain.Book
import com.example.bookapp.ui.theme.BookAppTheme
import com.example.bookapp.ui.theme.LightBlue
import com.example.bookapp.ui.theme.Sandyellow
import kotlin.math.absoluteValue


@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(32.dp),
        color = LightBlue.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(70.dp),
                contentAlignment = Alignment.Center
            ) {
                if (book.imageUrl != null){
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = "Обложка книги",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                else {
                    val gradient = getGradientForBook(book)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(gradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📚",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if(book.authors.isNotEmpty()) {
                    Text(
                        text = book.authors[0],
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (book.averageRating != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format("%.1f", book.averageRating),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Рейтинг",
                            tint = Sandyellow,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }


            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun getGradientForBook(book: Book): Brush {
    return when ((book.id.hashCode() % 4).absoluteValue) {
        0 -> Brush.verticalGradient(
            colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
        )
        1 -> Brush.verticalGradient(
            colors = listOf(Color(0xFFFF416C), Color(0xFFFF4B2B))
        )
        2 -> Brush.verticalGradient(
            colors = listOf(Color(0xFF00B09B), Color(0xFF96C93D))
        )
        else -> Brush.verticalGradient(
            colors = listOf(Color(0xFFDA4453), Color(0xFF89216B))
        )
    }
}
@Composable
@Preview
fun BookListItemPreview(){
    BookAppTheme {
        Box(
            modifier = Modifier
        ){
            BookListItem(
                book = Book(
                    id = "OL1234567W",
                    title = "The Great Gatsby",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BMTkxNTk1ODcxNl5BMl5BanBnXkFtZTcwMDI1OTMzOQ@@._V1_SX300.jpg",
                    authors = listOf("F. Scott Fitzgerald"),
                    description = "A classic novel about the American Dream",
                    languages = listOf("English"),
                    firstPublishYear = 1925,
                    averageRating = 4.2,
                    ratingCount = 1500,
                    numPages = 218,
                    numEditions = 50
                ),
                onClick = {},
                modifier = Modifier
            )
        }
    }
}